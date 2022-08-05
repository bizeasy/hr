import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { StatusValidChangeUpdateComponent } from 'app/entities/status-valid-change/status-valid-change-update.component';
import { StatusValidChangeService } from 'app/entities/status-valid-change/status-valid-change.service';
import { StatusValidChange } from 'app/shared/model/status-valid-change.model';

describe('Component Tests', () => {
  describe('StatusValidChange Management Update Component', () => {
    let comp: StatusValidChangeUpdateComponent;
    let fixture: ComponentFixture<StatusValidChangeUpdateComponent>;
    let service: StatusValidChangeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [StatusValidChangeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(StatusValidChangeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StatusValidChangeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StatusValidChangeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StatusValidChange(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new StatusValidChange();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
