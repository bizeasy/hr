import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISkillType } from 'app/shared/model/skill-type.model';
import { SkillTypeService } from './skill-type.service';
import { SkillTypeDeleteDialogComponent } from './skill-type-delete-dialog.component';

@Component({
  selector: 'sys-skill-type',
  templateUrl: './skill-type.component.html',
})
export class SkillTypeComponent implements OnInit, OnDestroy {
  skillTypes?: ISkillType[];
  eventSubscriber?: Subscription;

  constructor(protected skillTypeService: SkillTypeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.skillTypeService.query().subscribe((res: HttpResponse<ISkillType[]>) => (this.skillTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSkillTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISkillType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSkillTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('skillTypeListModification', () => this.loadAll());
  }

  delete(skillType: ISkillType): void {
    const modalRef = this.modalService.open(SkillTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.skillType = skillType;
  }
}
