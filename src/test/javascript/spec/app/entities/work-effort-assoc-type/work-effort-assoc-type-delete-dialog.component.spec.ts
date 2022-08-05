import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HrTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { WorkEffortAssocTypeDeleteDialogComponent } from 'app/entities/work-effort-assoc-type/work-effort-assoc-type-delete-dialog.component';
import { WorkEffortAssocTypeService } from 'app/entities/work-effort-assoc-type/work-effort-assoc-type.service';

describe('Component Tests', () => {
  describe('WorkEffortAssocType Management Delete Component', () => {
    let comp: WorkEffortAssocTypeDeleteDialogComponent;
    let fixture: ComponentFixture<WorkEffortAssocTypeDeleteDialogComponent>;
    let service: WorkEffortAssocTypeService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortAssocTypeDeleteDialogComponent],
      })
        .overrideTemplate(WorkEffortAssocTypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorkEffortAssocTypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkEffortAssocTypeService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
