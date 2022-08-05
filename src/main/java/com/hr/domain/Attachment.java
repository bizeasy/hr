package com.hr.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Attachment.
 */
@Entity
@Table(name = "attachment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 25)
    @Column(name = "name", length = 25)
    private String name;

    @Lob
    @Column(name = "file_attachment")
    private byte[] fileAttachment;

    @Column(name = "file_attachment_content_type")
    private String fileAttachmentContentType;

    @Column(name = "attachment_url")
    private String attachmentUrl;

    @Column(name = "mime_type")
    private String mimeType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Attachment name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFileAttachment() {
        return fileAttachment;
    }

    public Attachment fileAttachment(byte[] fileAttachment) {
        this.fileAttachment = fileAttachment;
        return this;
    }

    public void setFileAttachment(byte[] fileAttachment) {
        this.fileAttachment = fileAttachment;
    }

    public String getFileAttachmentContentType() {
        return fileAttachmentContentType;
    }

    public Attachment fileAttachmentContentType(String fileAttachmentContentType) {
        this.fileAttachmentContentType = fileAttachmentContentType;
        return this;
    }

    public void setFileAttachmentContentType(String fileAttachmentContentType) {
        this.fileAttachmentContentType = fileAttachmentContentType;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public Attachment attachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
        return this;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getMimeType() {
        return mimeType;
    }

    public Attachment mimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attachment)) {
            return false;
        }
        return id != null && id.equals(((Attachment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attachment{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fileAttachment='" + getFileAttachment() + "'" +
            ", fileAttachmentContentType='" + getFileAttachmentContentType() + "'" +
            ", attachmentUrl='" + getAttachmentUrl() + "'" +
            ", mimeType='" + getMimeType() + "'" +
            "}";
    }
}
