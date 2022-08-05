package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A CommunicationEvent.
 */
@Entity
@Table(name = "communication_event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CommunicationEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "entry_date")
    private ZonedDateTime entryDate;

    @Column(name = "subject")
    private String subject;

    @Column(name = "content")
    private String content;

    @Column(name = "from_string")
    private String fromString;

    @Column(name = "to_string")
    private String toString;

    @Column(name = "cc_string")
    private String ccString;

    @Column(name = "message")
    private String message;

    @Column(name = "date_started")
    private ZonedDateTime dateStarted;

    @Column(name = "date_ended")
    private ZonedDateTime dateEnded;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "info")
    private String info;

    @ManyToOne
    @JsonIgnoreProperties(value = "communicationEvents", allowSetters = true)
    private CommunicationEventType communicationEventType;

    @ManyToOne
    @JsonIgnoreProperties(value = "communicationEvents", allowSetters = true)
    private Status status;

    @ManyToOne
    @JsonIgnoreProperties(value = "communicationEvents", allowSetters = true)
    private ContactMechType contactMechType;

    @ManyToOne
    @JsonIgnoreProperties(value = "communicationEvents", allowSetters = true)
    private ContactMech contactMechFrom;

    @ManyToOne
    @JsonIgnoreProperties(value = "communicationEvents", allowSetters = true)
    private ContactMech contactMechTo;

    @ManyToOne
    @JsonIgnoreProperties(value = "communicationEvents", allowSetters = true)
    private Party fromParty;

    @ManyToOne
    @JsonIgnoreProperties(value = "communicationEvents", allowSetters = true)
    private Party toParty;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getEntryDate() {
        return entryDate;
    }

    public CommunicationEvent entryDate(ZonedDateTime entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public void setEntryDate(ZonedDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public String getSubject() {
        return subject;
    }

    public CommunicationEvent subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public CommunicationEvent content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFromString() {
        return fromString;
    }

    public CommunicationEvent fromString(String fromString) {
        this.fromString = fromString;
        return this;
    }

    public void setFromString(String fromString) {
        this.fromString = fromString;
    }

    public String getToString() {
        return toString;
    }

    public CommunicationEvent toString(String toString) {
        this.toString = toString;
        return this;
    }

    public void setToString(String toString) {
        this.toString = toString;
    }

    public String getCcString() {
        return ccString;
    }

    public CommunicationEvent ccString(String ccString) {
        this.ccString = ccString;
        return this;
    }

    public void setCcString(String ccString) {
        this.ccString = ccString;
    }

    public String getMessage() {
        return message;
    }

    public CommunicationEvent message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ZonedDateTime getDateStarted() {
        return dateStarted;
    }

    public CommunicationEvent dateStarted(ZonedDateTime dateStarted) {
        this.dateStarted = dateStarted;
        return this;
    }

    public void setDateStarted(ZonedDateTime dateStarted) {
        this.dateStarted = dateStarted;
    }

    public ZonedDateTime getDateEnded() {
        return dateEnded;
    }

    public CommunicationEvent dateEnded(ZonedDateTime dateEnded) {
        this.dateEnded = dateEnded;
        return this;
    }

    public void setDateEnded(ZonedDateTime dateEnded) {
        this.dateEnded = dateEnded;
    }

    public String getInfo() {
        return info;
    }

    public CommunicationEvent info(String info) {
        this.info = info;
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public CommunicationEventType getCommunicationEventType() {
        return communicationEventType;
    }

    public CommunicationEvent communicationEventType(CommunicationEventType communicationEventType) {
        this.communicationEventType = communicationEventType;
        return this;
    }

    public void setCommunicationEventType(CommunicationEventType communicationEventType) {
        this.communicationEventType = communicationEventType;
    }

    public Status getStatus() {
        return status;
    }

    public CommunicationEvent status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ContactMechType getContactMechType() {
        return contactMechType;
    }

    public CommunicationEvent contactMechType(ContactMechType contactMechType) {
        this.contactMechType = contactMechType;
        return this;
    }

    public void setContactMechType(ContactMechType contactMechType) {
        this.contactMechType = contactMechType;
    }

    public ContactMech getContactMechFrom() {
        return contactMechFrom;
    }

    public CommunicationEvent contactMechFrom(ContactMech contactMech) {
        this.contactMechFrom = contactMech;
        return this;
    }

    public void setContactMechFrom(ContactMech contactMech) {
        this.contactMechFrom = contactMech;
    }

    public ContactMech getContactMechTo() {
        return contactMechTo;
    }

    public CommunicationEvent contactMechTo(ContactMech contactMech) {
        this.contactMechTo = contactMech;
        return this;
    }

    public void setContactMechTo(ContactMech contactMech) {
        this.contactMechTo = contactMech;
    }

    public Party getFromParty() {
        return fromParty;
    }

    public CommunicationEvent fromParty(Party party) {
        this.fromParty = party;
        return this;
    }

    public void setFromParty(Party party) {
        this.fromParty = party;
    }

    public Party getToParty() {
        return toParty;
    }

    public CommunicationEvent toParty(Party party) {
        this.toParty = party;
        return this;
    }

    public void setToParty(Party party) {
        this.toParty = party;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommunicationEvent)) {
            return false;
        }
        return id != null && id.equals(((CommunicationEvent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommunicationEvent{" +
            "id=" + getId() +
            ", entryDate='" + getEntryDate() + "'" +
            ", subject='" + getSubject() + "'" +
            ", content='" + getContent() + "'" +
            ", fromString='" + getFromString() + "'" +
            ", toString='" + getToString() + "'" +
            ", ccString='" + getCcString() + "'" +
            ", message='" + getMessage() + "'" +
            ", dateStarted='" + getDateStarted() + "'" +
            ", dateEnded='" + getDateEnded() + "'" +
            ", info='" + getInfo() + "'" +
            "}";
    }
}
