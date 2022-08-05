import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContactMechPurpose } from 'app/shared/model/contact-mech-purpose.model';

@Component({
  selector: 'sys-contact-mech-purpose-detail',
  templateUrl: './contact-mech-purpose-detail.component.html',
})
export class ContactMechPurposeDetailComponent implements OnInit {
  contactMechPurpose: IContactMechPurpose | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactMechPurpose }) => (this.contactMechPurpose = contactMechPurpose));
  }

  previousState(): void {
    window.history.back();
  }
}
