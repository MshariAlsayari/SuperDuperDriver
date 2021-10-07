package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.STATUS;
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
        STATUS status;
        note.setUserid(user.getUserId());

        if (!noteService.isValidTitle(note.getNoteTitle())){
            status = STATUS.error;
            model.addAttribute("notes", this.noteService.getAllNotes(user.getUserId()));
            model.addAttribute("result", status.name());
            model.addAttribute("message", "The length of title should be less than 21");
            return "result";
        }

        if (!noteService.isValidDescription(note.getNoteDescription())){
            status = STATUS.error;
            model.addAttribute("notes", this.noteService.getAllNotes(user.getUserId()));
            model.addAttribute("result", status.name());
            model.addAttribute("message", "The length of description should be less than 1001");
            return "result";
        }


        if (noteService.isExist(note.getNoteId())) {
            noteService.updateNote(note);
            status = STATUS.success;
            model.addAttribute("message", "The note is updated");
        } else {

            if (noteService.isDuplicated(note)) {
                status = STATUS.error;
                model.addAttribute("message", "The note is duplicated");
            } else {
                noteService.createNote(note);
                model.addAttribute("message", "The note is created");
                status = STATUS.success;
            }
        }



        model.addAttribute("notes", this.noteService.getAllNotes(user.getUserId()));
        model.addAttribute("result", status.name());
        return "result";
    }

    @GetMapping("/{noteId}")
    public String deleteNote(Authentication authentication, @PathVariable(name = "noteId") Integer noteId, Model model) {
        User user = userService.getUser(authentication.getName());
        noteService.deleteNote(noteId);
        model.addAttribute("notes", this.noteService.getAllNotes(user.getUserId()));
        model.addAttribute("message", "The note is deleted");
        model.addAttribute("result",  STATUS.success.name());
        return "result";
    }
}
