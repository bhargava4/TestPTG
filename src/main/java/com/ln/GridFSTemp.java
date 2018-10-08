package com.ln;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.gridfs.GridFS;

@Configuration
public class GridFSTemp {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Value("${spring.data.mongodb.grid-fs-database}")
	private String dbName;

	@Bean
	public GridFS gridFS() {
		return new GridFS(mongoTemplate.getMongoDbFactory().getLegacyDb().getMongo().getDB(dbName));
	}

}
