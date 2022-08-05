import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { PartyClassificationTypeComponent } from 'app/entities/party-classification-type/party-classification-type.component';
import { PartyClassificationTypeService } from 'app/entities/party-classification-type/party-classification-type.service';
import { PartyClassificationType } from 'app/shared/model/party-classification-type.model';

describe('Component Tests', () => {
  describe('PartyClassificationType Management Component', () => {
    let comp: PartyClassificationTypeComponent;
    let fixture: ComponentFixture<PartyClassificationTypeComponent>;
    let service: PartyClassificationTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PartyClassificationTypeComponent],
      })
        .overrideTemplate(PartyClassificationTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyClassificationTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyClassificationTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PartyClassificationType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.partyClassificationTypes && comp.partyClassificationTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
