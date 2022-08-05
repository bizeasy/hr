import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HrTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { EmplPositionFulfillmentDeleteDialogComponent } from 'app/entities/empl-position-fulfillment/empl-position-fulfillment-delete-dialog.component';
import { EmplPositionFulfillmentService } from 'app/entities/empl-position-fulfillment/empl-position-fulfillment.service';

describe('Component Tests', () => {
  describe('EmplPositionFulfillment Management Delete Component', () => {
    let comp: EmplPositionFulfillmentDeleteDialogComponent;
    let fixture: ComponentFixture<EmplPositionFulfillmentDeleteDialogComponent>;
    let service: EmplPositionFulfillmentService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplPositionFulfillmentDeleteDialogComponent],
      })
        .overrideTemplate(EmplPositionFulfillmentDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmplPositionFulfillmentDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmplPositionFulfillmentService);
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
