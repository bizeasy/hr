import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ReasonTypeUpdateComponent } from 'app/entities/reason-type/reason-type-update.component';
import { ReasonTypeService } from 'app/entities/reason-type/reason-type.service';
import { ReasonType } from 'app/shared/model/reason-type.model';

describe('Component Tests', () => {
  describe('ReasonType Management Update Component', () => {
    let comp: ReasonTypeUpdateComponent;
    let fixture: ComponentFixture<ReasonTypeUpdateComponent>;
    let service: ReasonTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ReasonTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ReasonTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReasonTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReasonTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReasonType(123);
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
        const entity = new ReasonType();
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
