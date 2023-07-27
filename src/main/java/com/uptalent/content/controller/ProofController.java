package com.uptalent.content.controller;

import com.uptalent.content.model.Proof;
import com.uptalent.content.service.ProofService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/proofs")
public class ProofController {
    private final ProofService proofService;

    @PreAuthorize("permitAll()")
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public void save() {
        proofService.save();
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public List<Proof> findAll() {
        return proofService.findAll();
    }
}
