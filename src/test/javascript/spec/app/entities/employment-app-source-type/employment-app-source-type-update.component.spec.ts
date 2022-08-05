import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { EmploymentAppSourceTypeUpdateComponent } from 'app/entities/employment-app-source-type/employment-app-source-type-update.component';
import { EmploymentAppSourceTypeService } from 'app/entities/employment-app-source-type/employment-app-source-type.service';
import { EmploymentAppSourceType } from 'app/shared/model/employment-app-source-type.model';

describe('Component Tests', () => {
  describe('EmploymentAppSourceType Management Update Component', () => {
    let comp: EmploymentAppSourceTypeUpdateComponent;
    let fixture: ComponentFixture<EmploymentAppSourceTypeUpdateComponent>;
    let service: EmploymentAppSourceTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmploymentAppSourceTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmploymentAppSourceTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmploymentAppSourceTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmploymentAppSourceTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmploymentAppSourceType(123);
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
        const entity = new EmploymentAppSourceType();
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
