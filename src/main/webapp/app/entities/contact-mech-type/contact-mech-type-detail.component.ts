import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContactMechType } from 'app/shared/model/contact-mech-type.model';

@Component({
  selector: 'sys-contact-mech-type-detail',
  templateUrl: './contact-mech-type-detail.component.html',
})
export class ContactMechTypeDetailComponent implements OnInit {
  contactMechType: IContactMechType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactMechType }) => (this.contactMechType = contactMechType));
  }

  previousState(): void {
    window.history.back();
  }
}
