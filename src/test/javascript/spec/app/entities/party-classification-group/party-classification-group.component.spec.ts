import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { PartyClassificationGroupComponent } from 'app/entities/party-classification-group/party-classification-group.component';
import { PartyClassificationGroupService } from 'app/entities/party-classification-group/party-classification-group.service';
import { PartyClassificationGroup } from 'app/shared/model/party-classification-group.model';

describe('Component Tests', () => {
  describe('PartyClassificationGroup Management Component', () => {
    let comp: PartyClassificationGroupComponent;
    let fixture: ComponentFixture<PartyClassificationGroupComponent>;
    let service: PartyClassificationGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PartyClassificationGroupComponent],
      })
        .overrideTemplate(PartyClassificationGroupComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyClassificationGroupComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyClassificationGroupService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PartyClassificationGroup(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.partyClassificationGroups && comp.partyClassificationGroups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
