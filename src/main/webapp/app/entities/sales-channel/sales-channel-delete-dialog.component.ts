import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISalesChannel } from 'app/shared/model/sales-channel.model';
import { SalesChannelService } from './sales-channel.service';

@Component({
  templateUrl: './sales-channel-delete-dialog.component.html',
})
export class SalesChannelDeleteDialogComponent {
  salesChannel?: ISalesChannel;

  constructor(
    protected salesChannelService: SalesChannelService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.salesChannelService.delete(id).subscribe(() => {
      this.eventManager.broadcast('salesChannelListModification');
      this.activeModal.close();
    });
  }
}
