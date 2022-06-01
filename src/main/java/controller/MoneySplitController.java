package controller;

import dao.Details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import service.MoneySplitService;

import java.util.List;

public class MoneySplitController {

    @Autowired
    MoneySplitService moneySplitService;

    @PostMapping(value="/getSplitted")
    public ResponseEntity<String> getSplitted(@RequestBody List<Details> details){
    return new ResponseEntity<>(moneySplitService.getSplitWiseMoney(details), HttpStatus.OK);
    }
}
