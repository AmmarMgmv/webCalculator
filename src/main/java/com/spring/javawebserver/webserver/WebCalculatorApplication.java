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
import org.springframework.web.bind.annotation.*;

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
        return description + " = " + id;
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
            // check if equation is valid, if not print error message
            try {
                notesRepository.save(new Note(App.calculate(description), description.trim()));
            }catch(Exception e){
                notesRepository.save(new Note("This is not a valid expression. A valid input contains only integers and operands such as +, - and *", description.trim()));
            }
            model.addAttribute("description", "");
        }
    }


}


