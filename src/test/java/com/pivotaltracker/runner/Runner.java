package com.pivotaltracker.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"com.pivotaltracker"}
)
public class Runner extends AbstractTestNGCucumberTests {

}
