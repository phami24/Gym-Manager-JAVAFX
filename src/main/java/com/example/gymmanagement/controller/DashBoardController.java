package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.Members;
import com.example.gymmanagement.model.entity.Transaction;
import com.example.gymmanagement.model.repository.*;
import com.example.gymmanagement.model.service.*;
import com.example.gymmanagement.model.service.impl.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.poi.ss.formula.functions.T;


import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

public class DashBoardController implements Initializable {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private final StageManager stageManager = new StageManager();

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
    private ComboBox<Integer> yearRevenueBtn;
    @FXML
    private LineChart<String, BigDecimal> lineChart;

    @FXML
    private TableColumn<Transaction, String> nameColumn;
    @FXML
    private TableColumn<Transaction, String> transactionTypeColumn;
    @FXML
    private TableColumn<Transaction, String> transactionDateColumn;
    @FXML
    private TableColumn<Transaction, Double> amountColumn;
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<Transaction> transactionTableView;


    private ObservableList<Transaction> transactionData = FXCollections.observableArrayList();

    private final TransactionRepository transactionRepository = new TransactionRepository();

    private int currentPage = 1;
    private int pageSize = 9;

    private int totalPage = transactionRepository.getTotalTransactions() / pageSize;
    @FXML
    private Button minimizeButton;
    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);

    }

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
    public void getRevenueTotal() {
        BigDecimal totalRevenue = revenueRepository.getTotalRevenue();
        revenueNUM.setText(totalRevenue.toString());
    }

    RevenueService serviceWage = new RevenueServiceImpl();

    //take data(year) from MenuButton
    public void valueToChartByYear() {
        Integer selectedYear = yearRevenueBtn.getValue();
        if (selectedYear != null) {
            fetchChartData(selectedYear);
        }
    }

    //    fetch data(year) from MenuButton, then create LineChart add X,Y to display Yearly Revenue
    public void fetchChartData(Integer selectedValue) {
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
    @FXML
    public void close(MouseEvent event) {
        stageManager.loadHomeStage();
        stage.close();
    }

    @FXML
    void homepage(MouseEvent event) {
        stageManager.loadHomeStage();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        transactionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
        transactionDateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        nameColumn.setCellValueFactory(cellData -> {
            String transactionType = cellData.getValue().getTransactionType(); // Get the transaction type
            Long memberId = cellData.getValue().getMemberId(); // Get member_id
            Long equipmentId = cellData.getValue().getEquipmentId(); // Get equipment_id

            if (transactionType != null) {
                if (transactionType.startsWith("Membership Purchase") || transactionType.startsWith("Membership Renewal")) {
                    return new SimpleStringProperty(membersRepository.getMemberById(Integer.parseInt(memberId.toString())).getFirst_name() +membersRepository.getMemberById(Integer.parseInt(memberId.toString())).getLast_name());
                } else if (transactionType.startsWith("Equipment Purchase")) {
                    String equipmentName = equipmentRepository.getEquipmentNameById(equipmentId); // Get equipment name
                    return new SimpleStringProperty(equipmentName);
                }
            }
            if (equipmentId != null) {
                String equipmentName = equipmentRepository.getEquipmentNameById(equipmentId);
                return new SimpleStringProperty(equipmentName);
            }

            return new SimpleStringProperty("");
        });
        pagination.setPageCount(totalPage);
        pagination.setCurrentPageIndex(currentPage - 1);
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            currentPage = newIndex.intValue() + 1;
            loadTransactionData();
        });
        loadTransactionData();
        getMembernum();
        getClassnum();
        getPTnum();
        getEquipmentnum();
        getRevenueTotal();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Integer> years = new ArrayList<>();
        for (int year = 2022; year <= currentYear; year++) {
            years.add(year);
        }

        // Đặt danh sách năm vào ComboBox
        yearRevenueBtn.getItems().addAll(years);
        //Set default
        yearRevenueBtn.setValue(currentYear);
        fetchChartData(currentYear);
        yearRevenueBtn.setOnAction(event -> valueToChartByYear());
    }


    @FXML
    private void previousPage() {
        if (currentPage > 1) {
            pagination.setCurrentPageIndex(currentPage - 2); // Đặt trang trước đó
            loadTransactionData();
        }
    }

    @FXML
    private void nextPage() {
        if (currentPage < totalPage) {
            pagination.setCurrentPageIndex(currentPage); // Đặt trang tiếp theo
            loadTransactionData();
        }
    }

    private void loadTransactionData() {
        List<Transaction> transactionList = transactionRepository.getTransactionsByPage(currentPage, pageSize);
        transactionData.clear();
        transactionData.addAll(transactionList);
        transactionTableView.setItems(transactionData);
    }

}
