import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PartyClassificationUpdateComponent } from 'app/entities/party-classification/party-classification-update.component';
import { PartyClassificationService } from 'app/entities/party-classification/party-classification.service';
import { PartyClassification } from 'app/shared/model/party-classification.model';

describe('Component Tests', () => {
  describe('PartyClassification Management Update Component', () => {
    let comp: PartyClassificationUpdateComponent;
    let fixture: ComponentFixture<PartyClassificationUpdateComponent>;
    let service: PartyClassificationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PartyClassificationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PartyClassificationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyClassificationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyClassificationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PartyClassification(123);
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
        const entity = new PartyClassification();
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
