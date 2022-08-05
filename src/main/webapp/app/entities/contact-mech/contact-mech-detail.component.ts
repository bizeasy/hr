import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContactMech } from 'app/shared/model/contact-mech.model';

@Component({
  selector: 'sys-contact-mech-detail',
  templateUrl: './contact-mech-detail.component.html',
})
export class ContactMechDetailComponent implements OnInit {
  contactMech: IContactMech | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactMech }) => (this.contactMech = contactMech));
  }

  previousState(): void {
    window.history.back();
  }
}
