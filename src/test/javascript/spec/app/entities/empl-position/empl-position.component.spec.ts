import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { EmplPositionComponent } from 'app/entities/empl-position/empl-position.component';
import { EmplPositionService } from 'app/entities/empl-position/empl-position.service';
import { EmplPosition } from 'app/shared/model/empl-position.model';

describe('Component Tests', () => {
  describe('EmplPosition Management Component', () => {
    let comp: EmplPositionComponent;
    let fixture: ComponentFixture<EmplPositionComponent>;
    let service: EmplPositionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplPositionComponent],
      })
        .overrideTemplate(EmplPositionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmplPositionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmplPositionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EmplPosition(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.emplPositions && comp.emplPositions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
