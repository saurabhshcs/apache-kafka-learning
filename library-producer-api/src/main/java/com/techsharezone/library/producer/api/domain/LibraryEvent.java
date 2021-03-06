package com.techsharezone.library.producer.api.domain;

/*
 * @project library-producer
 * @author  saurabhshcs
 */


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
 public class LibraryEvent  {

    private Integer libraryEventId;
    private Book book;
    private LibraryEventType libraryEventType;
}
