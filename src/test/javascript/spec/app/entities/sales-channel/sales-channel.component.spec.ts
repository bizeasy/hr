import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { SalesChannelComponent } from 'app/entities/sales-channel/sales-channel.component';
import { SalesChannelService } from 'app/entities/sales-channel/sales-channel.service';
import { SalesChannel } from 'app/shared/model/sales-channel.model';

describe('Component Tests', () => {
  describe('SalesChannel Management Component', () => {
    let comp: SalesChannelComponent;
    let fixture: ComponentFixture<SalesChannelComponent>;
    let service: SalesChannelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [SalesChannelComponent],
      })
        .overrideTemplate(SalesChannelComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SalesChannelComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SalesChannelService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SalesChannel(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.salesChannels && comp.salesChannels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
