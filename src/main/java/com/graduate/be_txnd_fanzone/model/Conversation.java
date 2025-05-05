package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "conversation")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conversation_id", nullable = false)
    Long id;

    //0: private, 1: group
    @Column(name = "type")
    Byte type;

    @OneToMany(mappedBy = "conversation")
    List<ConversationMember> members;

    @OneToMany(mappedBy = "conversation")
    List<Message> messages;
}
