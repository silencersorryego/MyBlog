package com.pc.blog.mapper.es;

import com.pc.blog.domain.Blog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBlogMapper_ES extends ElasticsearchRepository<Blog,Integer> {
}
