import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PayGradeUpdateComponent } from 'app/entities/pay-grade/pay-grade-update.component';
import { PayGradeService } from 'app/entities/pay-grade/pay-grade.service';
import { PayGrade } from 'app/shared/model/pay-grade.model';

describe('Component Tests', () => {
  describe('PayGrade Management Update Component', () => {
    let comp: PayGradeUpdateComponent;
    let fixture: ComponentFixture<PayGradeUpdateComponent>;
    let service: PayGradeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PayGradeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PayGradeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PayGradeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PayGradeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PayGrade(123);
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
        const entity = new PayGrade();
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
