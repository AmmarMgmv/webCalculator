package com.spring.javawebserver.webserver;

import java.util.Collections;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SpringBootApplication
public class WebCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebCalculatorApplication.class, args);
	}

}

interface NotesRepository extends MongoRepository<Note, String> {

}

@Document(collection = "notes")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
class Note {
    @Id
    private String id;
    private String description;


    @Override
    public String toString() {
        return description+" "+id;
    }
}







@Controller
class NoteController {

    @Autowired
    private NotesRepository notesRepository;


    @GetMapping("/")
    public String index(Model model) {
        getAllNotes(model);
        return "index";
    }

    @PostMapping("/note")
    public String saveNotes(@RequestParam String description,
                            @RequestParam(required = false) String add,
                            Model model) throws Exception {

        if (add != null && add.equals("Calculate solution")) {
            saveNote(description, model);
            getAllNotes(model);
            return "redirect:/";
        }
        return "index";
    }


    private void getAllNotes(Model model) {
        List<Note> notes = notesRepository.findAll();
        Collections.reverse(notes);
        model.addAttribute("notes", notes);
    }



    private void saveNote(String description, Model model) {
        if (description != null && !description.trim().isEmpty()) {
        	java.util.Date timeStamp=new java.util.Date();
            notesRepository.save(new Note(timeStamp.toString(), description.trim()));
            model.addAttribute("description", "");
        }
    }

}

