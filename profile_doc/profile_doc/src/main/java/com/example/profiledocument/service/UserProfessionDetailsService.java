package com.example.profiledocument.service;

import com.example.profiledocument.model.UserProfessionDetails;
import com.example.profiledocument.utility.Utility;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.ExecutionException;

@Service
public class UserProfessionDetailsService {

    private static final String COLLECTION_NAME = "user_profession_details";

    private final Firestore firestore;

    public UserProfessionDetailsService(Firestore firestore) {
        this.firestore = firestore;
    }

    public String saveUserProfessionDetails(UserProfessionDetails details) throws ExecutionException, InterruptedException {
        DocumentReference document = firestore.collection(COLLECTION_NAME).document();
        details.setDocument_Id(document.getId());
        String formattedDate = Utility.getTime(LocalDateTime.now());
        details.setCreated_Date(formattedDate);
        details.setUpdated_Date(formattedDate);

        ApiFuture<WriteResult> writeResult = document.set(details);
        writeResult.get();
        return document.getId();
    }

    public UserProfessionDetails getUserProfessionDetailsById(String id) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = firestore.collection(COLLECTION_NAME).document(id).get().get();
        if (document.exists()) {
            return document.toObject(UserProfessionDetails.class);
        }
        return null;
    }

    public String deleteUserProfessionDetails(String id) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> writeResult = firestore.collection(COLLECTION_NAME).document(id).delete();
        writeResult.get();
        return "Document with ID " + id + " has been deleted.";
    }
}

