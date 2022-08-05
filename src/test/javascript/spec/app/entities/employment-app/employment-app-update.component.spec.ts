import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { EmploymentAppUpdateComponent } from 'app/entities/employment-app/employment-app-update.component';
import { EmploymentAppService } from 'app/entities/employment-app/employment-app.service';
import { EmploymentApp } from 'app/shared/model/employment-app.model';

describe('Component Tests', () => {
  describe('EmploymentApp Management Update Component', () => {
    let comp: EmploymentAppUpdateComponent;
    let fixture: ComponentFixture<EmploymentAppUpdateComponent>;
    let service: EmploymentAppService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmploymentAppUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmploymentAppUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmploymentAppUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmploymentAppService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmploymentApp(123);
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
        const entity = new EmploymentApp();
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