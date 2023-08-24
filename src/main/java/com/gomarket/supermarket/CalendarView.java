/*
 *  Copyright (C) 2017 Dirk Lemmermann Software & Consulting (dlsc.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.gomarket.supermarket;

import com.gomarket.supermarket.controllers.DateCellSkin;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.scene.control.Cell;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.layout.Region;
import javafx.util.Callback;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;

import static java.lang.Double.MAX_VALUE;
import static java.util.Objects.requireNonNull;
import static javafx.geometry.Pos.CENTER;

/**
 * Displays a given month of a given year. The view can be configured in many
 * ways:
 * <ul>
 * <li>Show / hide the name of the month</li>
 * <li>Show / hide the year</li>
 * <li>Show / hide arrow buttons for changing the month</li>
 * <li>Show / hide arrow buttons for changing the year</li>
 * <li>Show / hide today</li>
 * <li>Show / hide a button for going to today</li>
 * <li>Show / hide usage colors</li>
 * </ul>
 * Additionally the application can choose from two different behaviours when
 * the user clicks on a date:
 * <ol>
 * <li>Perform a selection / select the date</li>
 * <li>Show details of the date (by default shows a popover with all entries on
 * that date)</li>
 * </ol>
 * The image below shows the visual apperance of this control:
 *
 * <img src="doc-files/date-picker.png" alt="Date Picker">
 */
public class CalendarView extends Control {

    /**
     * Constructs a new view.
     */
    public CalendarView() {
        getStyleClass().add("calendar-view");
        setCellFactory(view -> new DateCell());
        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CalendarViewSkin(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return CalendarView.class.getResource("calendar-view.css").toExternalForm();
    }

    private final ObjectProperty<YearMonth> yearMonth = new SimpleObjectProperty<>(this, "yearMonth", YearMonth.now());

    /**
     * Stores the year and month shown by the control.
     *
     * @return the year and month
     */
    public final ObjectProperty<YearMonth> yearMonthProperty() {
        return yearMonth;
    }

    /**
     * Returns the value of {@link #yearMonthProperty()}.
     *
     * @return the year and month
     */
    public final YearMonth getYearMonth() {
        return yearMonth.get();
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth.set(yearMonth);
    }

    private final ObservableSet<DayOfWeek> weekendDays = FXCollections.observableSet();

    /**
     * Returns the days of the week that are considered to be weekend days, for
     * example Saturday and Sunday, or Friday and Saturday.
     *
     * @return the weekend days
     */
    public ObservableSet<DayOfWeek> getWeekendDays() {
        return weekendDays;
    }

    private final BooleanProperty showWeekNumbers = new SimpleBooleanProperty(this, "showWeekNumbers");

    /**
     * Controls whether the view will show week numbers.
     *
     * @return true if week numbers are shown
     */
    public final BooleanProperty showWeekNumbersProperty() {
        return showWeekNumbers;
    }

    /**
     * Sets the value of {@link #showWeekNumbersProperty()}.
     *
     * @param show if true will show week numbers
     */
    public final void setShowWeekNumbers(boolean show) {
        showWeekNumbersProperty().set(show);
    }

    /**
     * Returns the value of {@link #showWeekNumbersProperty()}.
     *
     * @return true if week numbers will be shown
     */
    public final boolean isShowWeekNumbers() {
        return showWeekNumbersProperty().get();
    }

    private final BooleanProperty showDaysOfPreviousOrNextMonth = new SimpleBooleanProperty(this, "showDaysOfPreviousOrNextMonth", true);

    public final boolean isShowDaysOfPreviousOrNextMonth() {
        return showDaysOfPreviousOrNextMonth.get();
    }

    /**
     * By default, the calendar for a given month might also show some days of the previous
     * and the next month. This property allows applications to hide them if needed.
     *
     * @return true if the calendar will be filled up with days of the previous and the next month
     */
    public final BooleanProperty showDaysOfPreviousOrNextMonthProperty() {
        return showDaysOfPreviousOrNextMonth;
    }

    public final void setShowDaysOfPreviousOrNextMonth(boolean showDaysOfPreviousOrNextMonth) {
        this.showDaysOfPreviousOrNextMonth.set(showDaysOfPreviousOrNextMonth);
    }

    private final BooleanProperty showToday = new SimpleBooleanProperty(this, "showToday", true);

    private final ObjectProperty<LocalDate> today = new SimpleObjectProperty<>(this, "today", LocalDate.now());

    /**
     * Stores the date that is considered to represent "today". This property is
     * initialized with {@link LocalDate#now()} but can be any date.
     *
     * @return the date representing "today"
     */
    public final ObjectProperty<LocalDate> todayProperty() {
        return today;
    }

    /**
     * Sets the value of {@link #todayProperty()}.
     *
     * @param date the date representing "today"
     */
    public final void setToday(LocalDate date) {
        requireNonNull(date);
        todayProperty().set(date);
    }

    /**
     * Returns the value of {@link #todayProperty()}.
     *
     * @return the date representing "today"
     */
    public final LocalDate getToday() {
        return todayProperty().get();
    }

    /**
     * A flag used to indicate that the view will mark the area that represents
     * the value of {@link #todayProperty()}. By default, this area will be
     * filled with a different color (red) than the rest (white).
     * <img src="doc-files/all-day-view-today.png" alt="All Day View Today">
     *
     * @return true if today will be shown differently
     */
    public final BooleanProperty showTodayProperty() {
        return showToday;
    }

    /**
     * Returns the value of {@link #showTodayProperty()}.
     *
     * @return true if today will be highlighted visually
     */
    public final boolean isShowToday() {
        return showTodayProperty().get();
    }

    /**
     * Sets the value of {@link #showTodayProperty()}.
     *
     * @param show if true today will be highlighted visually
     */
    public final void setShowToday(boolean show) {
        showTodayProperty().set(show);
    }

    private final BooleanProperty disablePreviousMonthButton = new SimpleBooleanProperty(this, "disablePreviousMonth");

    public final boolean isDisablePreviousMonthButton() {
        return disablePreviousMonthButton.get();
    }

    /**
     * A property to control whether the "show previous month" button will be disabled or not.
     * This property can be very useful when working with (for example) two calendars used
     * for selecting a date range. Then the second calendar should never show a month that
     * is earlier than the first calendar.
     *
     * @return true if the button used for going to the next month is currently disabled
     */
    public final BooleanProperty disablePreviousMonthButtonProperty() {
        return disablePreviousMonthButton;
    }

    public final void setDisablePreviousMonthButton(boolean disablePreviousMonthButton) {
        this.disablePreviousMonthButton.set(disablePreviousMonthButton);
    }

    private final BooleanProperty disableNextMonthButton = new SimpleBooleanProperty(this, "disablePreviousMonth");

    public final  boolean isDisableNextMonthButton() {
        return disableNextMonthButton.get();
    }

    /**
     * A property to control whether the "show next month" button will be disabled or not.
     * This property can be very useful when working with (for example) two calendars used
     * for selecting a date range. Then the first calendar should never show a month that
     * is later than the second calendar.
     *
     * @return true if the button used for going to the next month is currently disabled
     */
    public final BooleanProperty disableNextMonthButtonProperty() {
        return disableNextMonthButton;
    }

    public final void setDisableNextMonthButton(boolean disableNextMonthButton) {
        this.disableNextMonthButton.set(disableNextMonthButton);
    }

    private final BooleanProperty disableNextYearButton = new SimpleBooleanProperty(this, "disableNextYearButton");

    public final boolean isDisableNextYearButton() {
        return disableNextYearButton.get();
    }

    /**
     * A property to control whether the "show next year" button will be disabled or not.
     * This property can be very useful when working with (for example) two calendars used
     * for selecting a date range. Then the first calendar should never show a year that
     * is later than the second calendar.
     *
     * @return true if the button used for going to the next year is currently disabled
     */
    public final BooleanProperty disableNextYearButtonProperty() {
        return disableNextYearButton;
    }

    public final void setDisableNextYearButton(boolean disableNextYearButton) {
        this.disableNextYearButton.set(disableNextYearButton);
    }

    private final BooleanProperty disablePreviousYearButton = new SimpleBooleanProperty(this, "disablePreviousYearButton");

    public final boolean isDisablePreviousYearButton() {
        return disablePreviousYearButton.get();
    }

    /**
     * A property to control whether the "show previous year" button will be disabled or not.
     * This property can be very useful when working with (for example) two calendars used
     * for selecting a date range. Then the second calendar should never show a year that
     * is earlier than the first calendar.
     *
     * @return true if the button used for going to the next year is currently disabled
     */
    public final BooleanProperty disablePreviousYearButtonProperty() {
        return disablePreviousYearButton;
    }

    public final void setDisablePreviousYearButton(boolean disablePreviousYearButton) {
        this.disablePreviousYearButton.set(disablePreviousYearButton);
    }

    /**
     * The base date cell implementation for month views.
     *
     * @see #setCellFactory(Callback)
     */
    public static class DateCell extends Cell<LocalDate> {

        public DateCell() {
            getStyleClass().add("date-cell");
            setMaxSize(MAX_VALUE, MAX_VALUE);
            setAlignment(CENTER);
        }

        @Override
        protected Skin<?> createDefaultSkin() {
            return new DateCellSkin(this);
        }

        public final LocalDate getDate() {
            return getItem();
        }

        @Override
        public void updateItem(LocalDate date, boolean empty) {
            super.updateItem(date, empty);

            if (date != null) {
                setText(Integer.toString(date.getDayOfMonth()));
            }
        }
    }

    private final ObjectProperty<Callback<CalendarView, DateCell>> cellFactory = new SimpleObjectProperty<>(this, "cellFactory");

    /**
     * A factory for creating alternative content for the month view. The image
     * below shows the {@link CalendarView} once with the default factory and
     * once with an alternative factory that creates checkboxes.
     *
     * <img src="doc-files/month-cell-factory.png" alt="Month Cell Factory">
     *
     * @return the cell factory
     */
    public final ObjectProperty<Callback<CalendarView, DateCell>> cellFactoryProperty() {
        return cellFactory;
    }

    /**
     * Sets the value of {@link #cellFactoryProperty()}.
     *
     * @param factory the cell factory
     */
    public final void setCellFactory(Callback<CalendarView, DateCell> factory) {
        requireNonNull(factory);
        cellFactoryProperty().set(factory);
    }

    /**
     * Returns the value of {@link #cellFactoryProperty()}.
     *
     * @return the cell factory
     */
    public final Callback<CalendarView, DateCell> getCellFactory() {
        return cellFactoryProperty().get();
    }

    private final BooleanProperty showMonth = new SimpleBooleanProperty(this, "showMonth", true);

    public boolean isShowMonth() {
        return showMonth.get();
    }

    public BooleanProperty showMonthProperty() {
        return showMonth;
    }

    public void setShowMonth(boolean showMonth) {
        this.showMonth.set(showMonth);
    }

    private final BooleanProperty showYear = new SimpleBooleanProperty(this, "showYear", true);

    public final boolean isShowYear() {
        return showYear.get();
    }

    /**
     * Show or hide the year in the header.
     *
     * @return true if the year is shown in the header
     */
    public final BooleanProperty showYearProperty() {
        return showYear;
    }

    public final void setShowYear(boolean showYear) {
        this.showYear.set(showYear);
    }

    private final BooleanProperty showYearSpinner = new SimpleBooleanProperty(this, "showYearSpinner", true);

    /**
     * Show or hide the year / month spinner.
     *
     * @return true if the year will be shown
     */
    public final BooleanProperty showYearSpinnerProperty() {
        return showYearSpinner;
    }

    /**
     * Sets the value of {@link #showYearSpinnerProperty()}.
     *
     * @param show if true the year / month spinner at the top will be shown
     */
    public final void setShowYearSpinner(boolean show) {
        showYearSpinnerProperty().set(show);
    }

    /**
     * Returns the value of {@link #showYearSpinnerProperty()}.
     *
     * @return true if the year / month spinner will be shown
     */
    public final boolean isShowYearSpinner() {
        return showYearSpinnerProperty().get();
    }

    private final BooleanProperty showTodayButton = new SimpleBooleanProperty(this, "showTodayButton");

    /**
     * Show or hide a button to quickly go to today's date.
     *
     * @return true if the button will be shown
     */
    public final BooleanProperty showTodayButtonProperty() {
        return showTodayButton;
    }

    /**
     * Sets the value of the {@link #showTodayButtonProperty()}.
     *
     * @param show if true will show the button
     */
    public final void setShowTodayButton(boolean show) {
        showTodayButtonProperty().set(show);
    }

    /**
     * Returns the value of the {@link #showTodayButtonProperty()}.
     *
     * @return true if the button is shown
     */
    public final boolean isShowTodayButton() {
        return showTodayButtonProperty().get();
    }

    private final BooleanProperty showMonthArrows = new SimpleBooleanProperty(this, "showMonthArrows", true);

    /**
     * Shows or hides the arrows to change the month.
     *
     * @return true if the arrows will be shown
     */
    public final BooleanProperty showMonthArrowsProperty() {
        return showMonthArrows;
    }

    /**
     * Sets the value of the {@link #showMonthArrowsProperty()}.
     *
     * @param show if true will show the arrows
     */
    public final void setShowMonthArrows(boolean show) {
        showMonthArrowsProperty().set(show);
    }

    /**
     * Returns the value of the {@link #showMonthArrowsProperty()}.
     *
     * @return true if the arrows will be shown
     */
    public final boolean isShowMonthArrows() {
        return showMonthArrowsProperty().get();
    }

    private final ObjectProperty<SelectionModel> selectionModel = new SimpleObjectProperty<>(this, "selectionModel", new SelectionModel());

    public final SelectionModel getSelectionModel() {
        return selectionModel.get();
    }

    public final ObjectProperty<SelectionModel> selectionModelProperty() {
        return selectionModel;
    }

    public final void setSelectionModel(SelectionModel selectionModel) {
        this.selectionModel.set(selectionModel);
    }

    public static class SelectionModel {

        public enum SelectionMode {
            SINGLE_DATE,
            MULTIPLE_DATES,
            DATE_RANGE
        }

        public SelectionModel() {
            selectionMode.addListener(it -> clearSelection());
        }

        public final void clearSelection() {
            setSelectedDate(null);
            setSelectedEndDate(null);
            getSelectedDates().clear();
        }

        private final ObjectProperty<SelectionMode> selectionMode = new SimpleObjectProperty<>(this, "selectionMode", SelectionMode.SINGLE_DATE);

        public final SelectionMode getSelectionMode() {
            return selectionMode.get();
        }

        public final ObjectProperty<SelectionMode> selectionModeProperty() {
            return selectionMode;
        }

        public final void setSelectionMode(SelectionMode selectionMode) {
            this.selectionMode.set(selectionMode);
        }

        public void clearAndSelect(LocalDate date) {
            clearSelection();
            select(date);
        }


        public void select(LocalDate date) {
            if (date == null) {
                return;
            }

            switch (getSelectionMode()) {
                case SINGLE_DATE:
                    setSelectedDate(date);
                    break;
                case MULTIPLE_DATES:
                    getSelectedDates().add(date);
                    break;
                case DATE_RANGE:
                    if (getSelectedDate() == null) {
                        setSelectedDate(date);
                    } else {
                        setSelectedEndDate(date);
                    }
                    break;
            }
        }

        public void clearSelection(LocalDate date) {
            switch (getSelectionMode()) {
                case SINGLE_DATE:
                    clearSelection();
                    break;
                case MULTIPLE_DATES:
                    getSelectedDates().remove(date);
                    break;
                case DATE_RANGE:
                    if (Objects.equals(getSelectedDate(), date)) {
                        setSelectedDate(null);
                    } else if (Objects.equals(getSelectedEndDate(), date)) {
                        setSelectedEndDate(null);
                    }
                    break;
            }
        }

        public boolean isSelected(LocalDate date) {
            if (date == null) {
                return false;
            }

            LocalDate selectedDate = getSelectedDate();
            switch (getSelectionMode()) {
                case SINGLE_DATE:
                    return Objects.equals(selectedDate, date);
                case MULTIPLE_DATES:
                    return getSelectedDates().contains(date);
                case DATE_RANGE:
                    LocalDate selectedEndDate = getSelectedEndDate();
                    if (selectedDate == null && selectedEndDate == null) {
                        return false;
                    } else if (selectedDate != null && Objects.equals(selectedDate, date)) {
                        return true;
                    } else if (selectedDate != null && selectedEndDate != null) {
                        return !(date.isBefore(selectedDate) || date.isAfter(selectedEndDate));
                    }

                    return false;
            }

            return false;
        }

        private final ObjectProperty<LocalDate> selectedDate = new SimpleObjectProperty<>(this, "selectedDate");

        public final LocalDate getSelectedDate() {
            return selectedDate.get();
        }

        public final ObjectProperty<LocalDate> selectedDateProperty() {
            return selectedDate;
        }

        public final void setSelectedDate(LocalDate selectedDate) {
            this.selectedDate.set(selectedDate);
        }

        private final ObjectProperty<LocalDate> selectedEndDate = new SimpleObjectProperty<>(this, "endDate");

        public final LocalDate getSelectedEndDate() {
            return selectedEndDate.get();
        }

        public final ObjectProperty<LocalDate> selectedEndDateProperty() {
            return selectedEndDate;
        }

        public final void setSelectedEndDate(LocalDate selectedEndDate) {
            this.selectedEndDate.set(selectedEndDate);
        }

        private final ListProperty<LocalDate> selectedDates = new SimpleListProperty<>(this, "selectedDates", FXCollections.observableArrayList());

        public final ObservableList<LocalDate> getSelectedDates() {
            return selectedDates.get();
        }

        public final ListProperty<LocalDate> selectedDatesProperty() {
            return selectedDates;
        }

        public final void setSelectedDates(ObservableList<LocalDate> selectedDates) {
            this.selectedDates.set(selectedDates);
        }
    }
}
