import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HrTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { TaxAuthorityRateTypeDeleteDialogComponent } from 'app/entities/tax-authority-rate-type/tax-authority-rate-type-delete-dialog.component';
import { TaxAuthorityRateTypeService } from 'app/entities/tax-authority-rate-type/tax-authority-rate-type.service';

describe('Component Tests', () => {
  describe('TaxAuthorityRateType Management Delete Component', () => {
    let comp: TaxAuthorityRateTypeDeleteDialogComponent;
    let fixture: ComponentFixture<TaxAuthorityRateTypeDeleteDialogComponent>;
    let service: TaxAuthorityRateTypeService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [TaxAuthorityRateTypeDeleteDialogComponent],
      })
        .overrideTemplate(TaxAuthorityRateTypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaxAuthorityRateTypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaxAuthorityRateTypeService);
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
