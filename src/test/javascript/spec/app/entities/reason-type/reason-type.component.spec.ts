import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { ReasonTypeComponent } from 'app/entities/reason-type/reason-type.component';
import { ReasonTypeService } from 'app/entities/reason-type/reason-type.service';
import { ReasonType } from 'app/shared/model/reason-type.model';

describe('Component Tests', () => {
  describe('ReasonType Management Component', () => {
    let comp: ReasonTypeComponent;
    let fixture: ComponentFixture<ReasonTypeComponent>;
    let service: ReasonTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ReasonTypeComponent],
      })
        .overrideTemplate(ReasonTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReasonTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReasonTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ReasonType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.reasonTypes && comp.reasonTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
