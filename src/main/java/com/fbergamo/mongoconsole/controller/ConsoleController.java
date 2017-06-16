package com.fbergamo.mongoconsole.controller;

import com.fbergamo.mongoconsole.model.DatabaseQuery;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ConsoleController {

    private final MongoClient client;

    private ConsoleController(){
        client = new MongoClient();
    }

    @RequestMapping("/getDatabases")
    public ArrayList<String> getDatabases(){
        return client.listDatabaseNames().into(new ArrayList<>());
    }

    @PostMapping("/getCollections")
    public ArrayList<String> getCollections(@RequestBody DatabaseQuery dbq ){
        return client.getDatabase(dbq.getDatabaseName()).listCollectionNames().into(new ArrayList<> ());
    }

    @PostMapping("/findAll")
    public Collection<Document> findAll(@RequestBody DatabaseQuery dbq){
       MongoCollection coll = client.getDatabase(dbq.getDatabaseName()).getCollection(dbq.getCollectionName());
       return coll.find().limit(50).into(new ArrayList<Document>());
    }
}


