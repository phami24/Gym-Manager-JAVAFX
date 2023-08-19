package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.repository.*;
import com.example.gymmanagement.model.service.*;
import com.example.gymmanagement.model.service.impl.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;


import java.math.BigDecimal;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {
    private final MembersRepository membersRepository = new MembersRepository();
    private MembersService membersService = new MemberServiceImpl();

    private final InstructorRepository instructorRepository = new InstructorRepository();
    private InstructorsService instructorsService = new InstructorsServiceImpl();

    private final ClassesRepository classesRepository = new ClassesRepository();
    private ClassesService classesService = new ClassesServiceImpl();

    private final EquipmentRepository equipmentRepository = new EquipmentRepository();
    private EquipmentService equipmentService = new EquipmentServiceImpl();

    private final RevenueRepository revenueRepository = new RevenueRepository();
    private RevenueService revenueService = new RevenueServiceImpl();


    private Stage stage;
    @FXML
    private Label classNum;
    @FXML
    private Label exit;
    @FXML
    private Label memberNum;
    @FXML
    private Label staffNum;
    @FXML
    private Label revenueNUM;
    @FXML
    private Label stuffNum;
    @FXML
    private MenuButton yearRevenueBtn;
    @FXML
    private LineChart<String, BigDecimal> lineChart;
    @FXML
    private BarChart<?, ?> barChart;

    //    get total of data from database
    public void getMembernum() {
        int getMembersNum = membersRepository.getTotalMembers();
        memberNum.setText(Integer.toString(getMembersNum));
    }

    //    get total of data from database
    public void getClassnum() {
        int getClassNum = classesRepository.getTotalClasses();
        classNum.setText(Integer.toString(getClassNum));
    }

    //    get total of data from database
    public void getPTnum() {
        int getStaffNum = instructorRepository.getTotalInstructor();
        staffNum.setText(Integer.toString(getStaffNum));
    }

    //    get total of data from database
    public void getEquipmentnum() {
        int getStuffNum = equipmentRepository.getTotalEquipment();
        stuffNum.setText(Integer.toString(getStuffNum));
    }

    //    get total of data from database
    public void getRevenue() {
        int getSalesNum = revenueRepository.getTotalRevenue();
        revenueNUM.setText(Integer.toString(getSalesNum));
    }

    RevenueService serviceWage = new RevenueServiceImpl();
//take data(year) from MenuButton
    public void valueToChart() {
        yearRevenueBtn.getItems().forEach(menuItem -> {
                Integer selectedValue = Integer.parseInt(menuItem.getText());
                fetchChartData(selectedValue);
        });
    }
//    fetch data(year) from MenuButton, then create LineChart add X,Y to display Yearly Revenue
    public void fetchChartData(Integer selectedValue){
        BigDecimal januaryRevenue = serviceWage.calculateTotalRevenueByMonth(selectedValue, 1);
        BigDecimal februaryRevenue = serviceWage.calculateTotalRevenueByMonth(selectedValue, 2);
        BigDecimal marchRevenue = serviceWage.calculateTotalRevenueByMonth(selectedValue, 3);
        BigDecimal aprilRevenue = serviceWage.calculateTotalRevenueByMonth(selectedValue, 4);
        BigDecimal mayRevenue = serviceWage.calculateTotalRevenueByMonth(selectedValue, 5);
        BigDecimal juneRevenue = serviceWage.calculateTotalRevenueByMonth(selectedValue, 6);
        BigDecimal julyRevenue = serviceWage.calculateTotalRevenueByMonth(selectedValue, 7);
        BigDecimal augustRevenue = serviceWage.calculateTotalRevenueByMonth(selectedValue, 8);
        BigDecimal septemberRevenue = serviceWage.calculateTotalRevenueByMonth(selectedValue, 9);
        BigDecimal octoberRevenue = serviceWage.calculateTotalRevenueByMonth(selectedValue, 10);
        BigDecimal novemberRevenue = serviceWage.calculateTotalRevenueByMonth(selectedValue, 11);
        BigDecimal decemberRevenue = serviceWage.calculateTotalRevenueByMonth(selectedValue, 12);
        BigDecimal bigdecimal = new BigDecimal(100);
        XYChart.Series<String, BigDecimal> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data("1", (januaryRevenue.multiply(bigdecimal))));
        series.getData().add(new XYChart.Data("2", (februaryRevenue.multiply(bigdecimal))));
        series.getData().add(new XYChart.Data("3", (marchRevenue.multiply(bigdecimal))));
        series.getData().add(new XYChart.Data("4", (aprilRevenue.multiply(bigdecimal))));
        series.getData().add(new XYChart.Data("5", (mayRevenue.multiply(bigdecimal))));
        series.getData().add(new XYChart.Data("6", (juneRevenue.multiply(bigdecimal))));
        series.getData().add(new XYChart.Data("7", (julyRevenue.multiply(bigdecimal))));
        series.getData().add(new XYChart.Data("8", (augustRevenue.multiply(bigdecimal))));
        series.getData().add(new XYChart.Data("9", (septemberRevenue.multiply(bigdecimal))));
        series.getData().add(new XYChart.Data("10", (octoberRevenue.multiply(bigdecimal))));
        series.getData().add(new XYChart.Data("11", (novemberRevenue.multiply(bigdecimal))));
        series.getData().add(new XYChart.Data("12", (decemberRevenue.multiply(bigdecimal))));
        lineChart.getData().clear();
        lineChart.getData().add(series);
    }
//    Back to home page or close stage
//        @FXML
//    public void exit(MouseEvent event){
//        stage.close();
//    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        yearRevenueBtn.setText(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
        getMembernum();
        getClassnum();
        getPTnum();
        getEquipmentnum();
        getRevenue();
        valueToChart();
//        exit();
    }
}
