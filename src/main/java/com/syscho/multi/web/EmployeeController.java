package com.syscho.multi.web;

import com.syscho.multi.lib.filter.TenantContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final ExecutorService contextAwareExecutorService;

    public EmployeeController(EmployeeRepository employeeRepository, ExecutorService contextAwareExecutorService) {
        this.employeeRepository = employeeRepository;
        this.contextAwareExecutorService = contextAwareExecutorService;
    }

    @GetMapping
    public List<Employee> getAll() throws ExecutionException, InterruptedException {
        Future<List<Employee>> submit = contextAwareExecutorService.submit(() -> employeeRepository.findAll());
        return submit.get();
    }

    @GetMapping("/async")
    public void printAsync() {
        runAsync();
    }

    @Async
    private void runAsync() {
        System.out.println("current environment :  " + TenantContextHolder.getEnv());
        employeeRepository.findAll().forEach(System.out::println);
    }

}
