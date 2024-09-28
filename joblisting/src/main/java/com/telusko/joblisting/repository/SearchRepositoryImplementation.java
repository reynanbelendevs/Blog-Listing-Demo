package com.telusko.joblisting.repository;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.telusko.joblisting.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Repository;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




@Repository
public class SearchRepositoryImplementation implements  SearchRepository {

    @Autowired
    MongoClient client;
    @Autowired
    MongoConverter converter;
    @Override
    public List<Post> findByText(String text) {

        final List<Post> posts = new ArrayList<Post>();
        MongoDatabase database = client.getDatabase("telusko");
        MongoCollection<Document> collection = database.getCollection("JobPost");
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
                                new Document("text",
                                new Document("query", text)
                                        .append("path", Arrays.asList("techs", "profile", "desc")))),
                                new Document("$sort", new Document("exp", 1L)),
                                new Document("$limit", 5L)));
        result.forEach(doc -> {
            posts.add(converter.read(Post.class ,doc));
        });


        return posts;
    }
}
