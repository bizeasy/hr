import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPostalAddress } from 'app/shared/model/postal-address.model';
import { PostalAddressService } from './postal-address.service';

@Component({
  templateUrl: './postal-address-delete-dialog.component.html',
})
export class PostalAddressDeleteDialogComponent {
  postalAddress?: IPostalAddress;

  constructor(
    protected postalAddressService: PostalAddressService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.postalAddressService.delete(id).subscribe(() => {
      this.eventManager.broadcast('postalAddressListModification');
      this.activeModal.close();
    });
  }
}
