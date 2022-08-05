import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { PartyTypeComponent } from 'app/entities/party-type/party-type.component';
import { PartyTypeService } from 'app/entities/party-type/party-type.service';
import { PartyType } from 'app/shared/model/party-type.model';

describe('Component Tests', () => {
  describe('PartyType Management Component', () => {
    let comp: PartyTypeComponent;
    let fixture: ComponentFixture<PartyTypeComponent>;
    let service: PartyTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PartyTypeComponent],
      })
        .overrideTemplate(PartyTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PartyType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.partyTypes && comp.partyTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
