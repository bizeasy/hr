import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { EmplPositionUpdateComponent } from 'app/entities/empl-position/empl-position-update.component';
import { EmplPositionService } from 'app/entities/empl-position/empl-position.service';
import { EmplPosition } from 'app/shared/model/empl-position.model';

describe('Component Tests', () => {
  describe('EmplPosition Management Update Component', () => {
    let comp: EmplPositionUpdateComponent;
    let fixture: ComponentFixture<EmplPositionUpdateComponent>;
    let service: EmplPositionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplPositionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmplPositionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmplPositionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmplPositionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmplPosition(123);
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
        const entity = new EmplPosition();
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
