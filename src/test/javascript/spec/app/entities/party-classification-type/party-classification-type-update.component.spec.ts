import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PartyClassificationTypeUpdateComponent } from 'app/entities/party-classification-type/party-classification-type-update.component';
import { PartyClassificationTypeService } from 'app/entities/party-classification-type/party-classification-type.service';
import { PartyClassificationType } from 'app/shared/model/party-classification-type.model';

describe('Component Tests', () => {
  describe('PartyClassificationType Management Update Component', () => {
    let comp: PartyClassificationTypeUpdateComponent;
    let fixture: ComponentFixture<PartyClassificationTypeUpdateComponent>;
    let service: PartyClassificationTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PartyClassificationTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PartyClassificationTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyClassificationTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyClassificationTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PartyClassificationType(123);
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
        const entity = new PartyClassificationType();
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
