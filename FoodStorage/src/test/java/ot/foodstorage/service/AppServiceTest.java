package ot.foodstorage.service;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ot.foodstorage.dao.*;
import ot.foodstorage.database.Database;
import ot.foodstorage.domain.Food;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AppServiceTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.exit();
    }
}