import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { LocationTypeComponent } from 'app/entities/location-type/location-type.component';
import { LocationTypeService } from 'app/entities/location-type/location-type.service';
import { LocationType } from 'app/shared/model/location-type.model';

describe('Component Tests', () => {
  describe('LocationType Management Component', () => {
    let comp: LocationTypeComponent;
    let fixture: ComponentFixture<LocationTypeComponent>;
    let service: LocationTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [LocationTypeComponent],
      })
        .overrideTemplate(LocationTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LocationTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocationTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LocationType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.locationTypes && comp.locationTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
