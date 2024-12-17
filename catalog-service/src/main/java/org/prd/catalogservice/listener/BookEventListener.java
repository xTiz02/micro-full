package org.prd.catalogservice.listener;

import lombok.extern.slf4j.Slf4j;
import org.prd.catalogservice.model.dto.BookEventDto;
import org.prd.catalogservice.service.BookService;
import org.prd.catalogservice.service.CatalogService;
import org.prd.catalogservice.util.EventType;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;


@Configuration
public class BookEventListener {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(BookEventListener.class);

    private final BookService bookServ;

    public BookEventListener(BookService bookServ) {
        this.bookServ = bookServ;
    }


    @KafkaListener(topics = {"catalog-evt"}, groupId = "book-read" , containerFactory = "listenerContainerFactory")
    public void listener(BookEventDto event){
        log.info("Received event: {}", event);
        try{
            switch (event.eventType().name()){
                case "CREATE":
                    bookServ.createBook(event.book());
                    break;
                case "UPDATE":
                    bookServ.updateBook(event.code(),event.book());
                    break;
                case "DELETE":
                    bookServ.deleteBook(event.code());
                    break;
                case "ENABLE":
                    bookServ.changeStateBook(event.code(), event.enable());
                    break;
                default:
                    throw new RuntimeException("Event type not supported: " + event.eventType());
            }
        }catch (Exception e){
           log.error("Error processing event type : {}. Message: {}",event.eventType(), e.getMessage());
            //TODO: Guardar el evento en la tabla de eventos e intentar procesar nuevamente cada cierto tiempo con un scheduler
            e.printStackTrace();
        }

    }
}