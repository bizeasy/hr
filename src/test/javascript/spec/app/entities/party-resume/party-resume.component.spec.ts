import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { PartyResumeComponent } from 'app/entities/party-resume/party-resume.component';
import { PartyResumeService } from 'app/entities/party-resume/party-resume.service';
import { PartyResume } from 'app/shared/model/party-resume.model';

describe('Component Tests', () => {
  describe('PartyResume Management Component', () => {
    let comp: PartyResumeComponent;
    let fixture: ComponentFixture<PartyResumeComponent>;
    let service: PartyResumeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PartyResumeComponent],
      })
        .overrideTemplate(PartyResumeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyResumeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyResumeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PartyResume(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.partyResumes && comp.partyResumes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
