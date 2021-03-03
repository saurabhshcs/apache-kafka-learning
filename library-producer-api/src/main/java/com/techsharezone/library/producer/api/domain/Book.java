package com.techsharezone.library.producer.api.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @project library-producer
 * @author  saurabhshcs
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Book {

    private Integer id;
    private String name;
    private String author;
}
