package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A PartyResume.
 */
@Entity
@Table(name = "party_resume")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PartyResume implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "resume_date")
    private LocalDate resumeDate;

    @Lob
    @Column(name = "file_attachment")
    private byte[] fileAttachment;

    @Column(name = "file_attachment_content_type")
    private String fileAttachmentContentType;

    @Column(name = "attachment_url")
    private String attachmentUrl;

    @Column(name = "mime_type")
    private String mimeType;

    @ManyToOne
    @JsonIgnoreProperties(value = "partyResumes", allowSetters = true)
    private Party party;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public PartyResume text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getResumeDate() {
        return resumeDate;
    }

    public PartyResume resumeDate(LocalDate resumeDate) {
        this.resumeDate = resumeDate;
        return this;
    }

    public void setResumeDate(LocalDate resumeDate) {
        this.resumeDate = resumeDate;
    }

    public byte[] getFileAttachment() {
        return fileAttachment;
    }

    public PartyResume fileAttachment(byte[] fileAttachment) {
        this.fileAttachment = fileAttachment;
        return this;
    }

    public void setFileAttachment(byte[] fileAttachment) {
        this.fileAttachment = fileAttachment;
    }

    public String getFileAttachmentContentType() {
        return fileAttachmentContentType;
    }

    public PartyResume fileAttachmentContentType(String fileAttachmentContentType) {
        this.fileAttachmentContentType = fileAttachmentContentType;
        return this;
    }

    public void setFileAttachmentContentType(String fileAttachmentContentType) {
        this.fileAttachmentContentType = fileAttachmentContentType;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public PartyResume attachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
        return this;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getMimeType() {
        return mimeType;
    }

    public PartyResume mimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Party getParty() {
        return party;
    }

    public PartyResume party(Party party) {
        this.party = party;
        return this;
    }

    public void setParty(Party party) {
        this.party = party;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartyResume)) {
            return false;
        }
        return id != null && id.equals(((PartyResume) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartyResume{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", resumeDate='" + getResumeDate() + "'" +
            ", fileAttachment='" + getFileAttachment() + "'" +
            ", fileAttachmentContentType='" + getFileAttachmentContentType() + "'" +
            ", attachmentUrl='" + getAttachmentUrl() + "'" +
            ", mimeType='" + getMimeType() + "'" +
            "}";
    }
}
