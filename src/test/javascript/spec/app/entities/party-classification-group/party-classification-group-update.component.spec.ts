import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PartyClassificationGroupUpdateComponent } from 'app/entities/party-classification-group/party-classification-group-update.component';
import { PartyClassificationGroupService } from 'app/entities/party-classification-group/party-classification-group.service';
import { PartyClassificationGroup } from 'app/shared/model/party-classification-group.model';

describe('Component Tests', () => {
  describe('PartyClassificationGroup Management Update Component', () => {
    let comp: PartyClassificationGroupUpdateComponent;
    let fixture: ComponentFixture<PartyClassificationGroupUpdateComponent>;
    let service: PartyClassificationGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PartyClassificationGroupUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PartyClassificationGroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyClassificationGroupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyClassificationGroupService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PartyClassificationGroup(123);
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
        const entity = new PartyClassificationGroup();
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
