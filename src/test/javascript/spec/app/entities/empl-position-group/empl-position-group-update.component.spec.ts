import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { EmplPositionGroupUpdateComponent } from 'app/entities/empl-position-group/empl-position-group-update.component';
import { EmplPositionGroupService } from 'app/entities/empl-position-group/empl-position-group.service';
import { EmplPositionGroup } from 'app/shared/model/empl-position-group.model';

describe('Component Tests', () => {
  describe('EmplPositionGroup Management Update Component', () => {
    let comp: EmplPositionGroupUpdateComponent;
    let fixture: ComponentFixture<EmplPositionGroupUpdateComponent>;
    let service: EmplPositionGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplPositionGroupUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmplPositionGroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmplPositionGroupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmplPositionGroupService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmplPositionGroup(123);
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
        const entity = new EmplPositionGroup();
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
