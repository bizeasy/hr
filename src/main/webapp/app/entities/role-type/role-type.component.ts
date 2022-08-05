import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRoleType } from 'app/shared/model/role-type.model';
import { RoleTypeService } from './role-type.service';
import { RoleTypeDeleteDialogComponent } from './role-type-delete-dialog.component';

@Component({
  selector: 'sys-role-type',
  templateUrl: './role-type.component.html',
})
export class RoleTypeComponent implements OnInit, OnDestroy {
  roleTypes?: IRoleType[];
  eventSubscriber?: Subscription;

  constructor(protected roleTypeService: RoleTypeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.roleTypeService.query().subscribe((res: HttpResponse<IRoleType[]>) => (this.roleTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRoleTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRoleType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRoleTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('roleTypeListModification', () => this.loadAll());
  }

  delete(roleType: IRoleType): void {
    const modalRef = this.modalService.open(RoleTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.roleType = roleType;
  }
}
