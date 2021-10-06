package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("notes")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping()
    public String submitNote(Authentication authentication, @ModelAttribute Note note, Model model) {
        User user = userService.getUser(authentication.getName());
        note.setUserid(user.getUserId());

        if (noteService.isExist(note.getNoteId())) {
            model.addAttribute("addNoteSuccess", true);
            noteService.updateNote(note);
        } else {

            if (noteService.isDuplicated(note)) {
                model.addAttribute("addNoteError", true);
            } else {
                model.addAttribute("addNoteSuccess", true);
                noteService.createNote(note);
            }
        }



        model.addAttribute("notes", this.noteService.getAllNotes());
        return "home";
    }

    @GetMapping("/{noteId}")
    public String deleteNote(@PathVariable(name = "noteId") Integer noteId, Model model) {
        noteService.deleteNote(noteId);
        model.addAttribute("notes", this.noteService.getAllNotes());
        return "home";
    }
}
