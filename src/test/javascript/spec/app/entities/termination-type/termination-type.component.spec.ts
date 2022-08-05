import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { TerminationTypeComponent } from 'app/entities/termination-type/termination-type.component';
import { TerminationTypeService } from 'app/entities/termination-type/termination-type.service';
import { TerminationType } from 'app/shared/model/termination-type.model';

describe('Component Tests', () => {
  describe('TerminationType Management Component', () => {
    let comp: TerminationTypeComponent;
    let fixture: ComponentFixture<TerminationTypeComponent>;
    let service: TerminationTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [TerminationTypeComponent],
      })
        .overrideTemplate(TerminationTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TerminationTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TerminationTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TerminationType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.terminationTypes && comp.terminationTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
