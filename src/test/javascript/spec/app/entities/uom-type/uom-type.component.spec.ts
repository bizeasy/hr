import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { UomTypeComponent } from 'app/entities/uom-type/uom-type.component';
import { UomTypeService } from 'app/entities/uom-type/uom-type.service';
import { UomType } from 'app/shared/model/uom-type.model';

describe('Component Tests', () => {
  describe('UomType Management Component', () => {
    let comp: UomTypeComponent;
    let fixture: ComponentFixture<UomTypeComponent>;
    let service: UomTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [UomTypeComponent],
      })
        .overrideTemplate(UomTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UomTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UomTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UomType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.uomTypes && comp.uomTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
