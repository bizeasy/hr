import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PartyResumeUpdateComponent } from 'app/entities/party-resume/party-resume-update.component';
import { PartyResumeService } from 'app/entities/party-resume/party-resume.service';
import { PartyResume } from 'app/shared/model/party-resume.model';

describe('Component Tests', () => {
  describe('PartyResume Management Update Component', () => {
    let comp: PartyResumeUpdateComponent;
    let fixture: ComponentFixture<PartyResumeUpdateComponent>;
    let service: PartyResumeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PartyResumeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PartyResumeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyResumeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyResumeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PartyResume(123);
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
        const entity = new PartyResume();
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
